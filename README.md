# Money-tracker
Project base on Kotlin and VueJs


# Elastic APM Monitoring

- Elastic RUM - this agent for front end.
- Elastic APM - this agent for back end.

## Enable RUM on VueJs

Doc: https://www.elastic.co/guide/en/apm/agent/rum-js/master/vue-integration.html

Config rum.js file:

```javascript
    import Vue from 'vue';
    import {ApmVuePlugin} from '@elastic/apm-rum-vue';
    
    const apm = Vue.use(ApmVuePlugin, {
      config: {
         serviceName: 'ClientN-Frontend',
         serviceVersion: '0.90',
         // Set custom APM Server URL (default: http://localhost:8200)
         serverUrl: 'http://localhost:8200',
         // For distributed tracing to different origin (CORS)
         // distributedTracingOrigins: ['http://localhost:8080'],
      }
    });
    
    export default apm;
```

Connect to VueJs widget:

Option 1: Config main.js file:

```javascript
    import ApmRum from '../shared/rum.js';
    // imports ...

    // code ...
    vueWidget = new Vue({
      el: '#element-frame',
      rum: ApmRum,
      render: h => h(CardWidget, {
         // Component props
         props: {
            id: [_prop_]
         }
      })
    });
```

Option 2:

```vue
<script>
	import rumUtils from 'rum';
	export default {
		// code ..
		created()
		{
			rumUtils.useApm();
			this.$apm.setInitialPageLoadName('VueJs Window');
		}
	};
</script>
```
## Enable APM Agent:

```shell
    -javaagent:path_/elastic-apm-agent-1.18.1.jar
    -Delastic.apm.config_file=path_/elasticapm.properties
```

## Security:

- APM Server inputs: Java APM Agent, RUM JavaScript Agent
- APM Server outputs: Elasticsearch.

APM documentation:
https://www.elastic.co/guide/en/apm/server/current/secure-communication-agents.html

### How to configure Secret Token:

In order to connect APM Java Agent and APM Server with security, we have two options:

- API keys - this is experimental and may be changed or removed completely in a future release. (this information from elastic resource).
- Secret token - this is the most popular approach and we will consider this approach below.

We need to perform 3 steps:

- Enable SSL/TLS
- Generate APM Server keystore
- Set a secret token in your Agents and Server

### 1. Enable SSL/TLS
We will consider a TLS based on a self-signed certificate.
We need to pre-install elasticsearch-certutil, this tool is included in the elasticsearch package.

1. Creating a self-signed certificate (PEM format) with elasticsearch-certutil:
  
```shell
$ vi ./instance.yml
instances:
 - name: 'client_name_apm'
   dns: [ 'localhost' ]

$ bin/elasticsearch-certutil cert ca --pem --in ./instance.yml --out ./certs.zip
```

Official example here: https://www.elastic.co/guide/en/apm/server/current/ssl-setup.html

2. Unzip ./certs.zip file and use copy and paste under APM Server home directory. 
   Put SSL configuration in apm-server.yml like below.

```shell
apm-server.ssl.enabled: true
apm-server.ssl.key: "./data/client_n/client_name_apm.key"
apm-server.ssl.certificate: "./data/client_n/client_name_apm.crt"
```

3. Store CA to JKS for Java Agent on Tomcat
Need to store CA in JKS(Java KeyStore). For local APM Java Agent I have added to the JVM trustStore:
```shell
sudo keytool -import -alias localhost -file ./ca.crt -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_261.jdk/Contents/Home/jre/lib/security/cacerts
```
Note: We can check it in CATALINA_OPTS: -Djavax.net.ssl.trustStore=

### 2. Generate APM Server keystore

For greater security, we need to create a APM keystore.
Do it in docker container:
```shell
   docker exec -it [apm_container_id] /bin/bash
   ./apm-server keystore create
   ./apm-server keystore add SECRET_TOKEN
```
After that enter value for the key, for instance: _test_

Doc: https://www.elastic.co/guide/en/apm/server/current/keystore.html

### 3. Set a secret token in your Agents and Server

Put configuration in apm-server.yml like below:
```shell
   apm-server.secret_token: ${SECRET_TOKEN}
   Put configuration in elasticapm.properties like below:
   secret_token=test
```

Doc: https://www.elastic.co/guide/en/apm/server/current/secret-token.html#set-secret-token

