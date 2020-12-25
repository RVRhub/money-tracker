import Vue from 'vue';
import {ApmVuePlugin} from '@elastic/apm-rum-vue';

const useApm = () =>
{
	Vue.use(ApmVuePlugin, {
		config: {
			serviceName: 'money-tracker-rum',
			serviceVersion: '0.90',
			serverUrl: 'http://localhost:8700'
		}
	});
};

const startTransaction = (apm, name, params, isManaged) =>
{
	if (apm)
	{
		const transaction = apm.startTransaction(name, 'card', {managed: isManaged});
		transaction.addLabels(params);
		return transaction;
	}
	return null;
};

const startSpan = (transaction, name, isBlocking) =>
{
	if (transaction)
	{
		return transaction.startSpan(name,  'card', {blocking: isBlocking});
	}
	return null;
};

export default {
	useApm,
	startTransaction,
	startSpan
};
