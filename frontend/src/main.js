import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import ApmRum from './shared/rum.js';

Vue.config.productionTip = false;
ApmRum.useApm();

new Vue({
	vuetify,
	ApmRum,
	render: h => h(App)
}).$mount('#app');
