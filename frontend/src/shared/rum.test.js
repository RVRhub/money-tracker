import rumUtils from './rum';
import Vue from 'vue';
import {createLocalVue, shallowMount} from '@vue/test-utils';

let wrapper;
const vueWidget = new Vue();
const localVue = createLocalVue();

describe('APM RUM Utils', () =>
{
	beforeEach(() =>
	{
		// Mount only needed component
		window.location = {
			origin: 'http://localhost',
			pathname: '/app/1234'
		};

		wrapper = shallowMount(vueWidget, {
			localVue
		});
	});

	test('should not return transaction', () =>
	{
		const params = {['searchTerm']: 'test', ['searchVisitId']: 12};
		const transaction = rumUtils.startTransaction(wrapper.vm.$apm, 'Transaction Name', params);
		expect(wrapper.vm.$apm).toBeUndefined();
		expect(transaction).toBe(null);
	});

	test('should enable APM vue plugin', () =>
	{
		rumUtils.useApm();

		expect(wrapper.vm.$apm.serviceFactory.initialized).toBeTruthy();
		expect(wrapper.vm.$apm.serviceFactory.instances.ConfigService.config.serverUrl).toBe('http://localhost/apm-server');
		expect(wrapper.vm.$apm.serviceFactory.instances.ConfigService.config.serviceName).toBe('money-tracker-rum');
	});

	test('should return new apm transaction', () =>
	{
		// eslint-disable-next-line no-proto
		jest.spyOn(window.localStorage.__proto__, 'getItem');
		// eslint-disable-next-line no-proto
		window.localStorage.__proto__.getItem = jest.fn();

		rumUtils.useApm();

		const params = {['searchTerm']: 'test', ['searchVisitId']: 12};
		const transaction = rumUtils.startTransaction(wrapper.vm.$apm, 'Transaction Name', params);
		expect(transaction.name).toBe('Transaction Name');
		expect(transaction.context.tags.searchTerm).toBe('test');
		expect(transaction.context.tags.searchVisitId).toBe(12);
	});

	test('should return new apm span', () =>
	{
		rumUtils.useApm();
		const transaction = rumUtils.startTransaction(wrapper.vm.$apm, 'Transaction Name', {});
		const span = rumUtils.startSpan(transaction, 'Span Name');
		expect(span.name).toBe('Span Name');
	});
});
