<template>
	<v-app>
		<v-main class="d-flex justify-center">
			<div class="search-box">
			<h1>Test Cart</h1>
			</div>
			<v-row align="center" justify="center">
        <cart></cart>
			</v-row>
		</v-main>
	</v-app>
</template>

<script>

import Cart from '@/components/Cart';

export default {
	name: 'App',
	components: {
		Cart
	},
	created()
	{
		this.$apm.setInitialPageLoadName('App Window');
	},
	data: () => ({
		search: '',
		notification: {
			color: 'success',
			message: '',
			show: false
		}
	})
};
</script>

<style lang="scss">


.search-box {
	max-width: 500px;
	margin: 0 auto;
}

</style>
