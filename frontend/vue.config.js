//module.exports = {
//	'transpileDependencies': [
//		'vuetify'
//	],
//	// Proxy?
//	devServer: {
//		proxy: {
//			'^/gateway-service': {
//				target: 'http://localhost:3000',
//				pathRewrite: {'^/gateway-service': ''},
//				ws: true,
//				changeOrigin: true,
//
//			}
//		}
//	},
//}
module.exports = {
	outputDir: 'build/dist',
	assetsDir: 'static',
	devServer: {
		host: 'localhost',
		port: 8081
	}
};
