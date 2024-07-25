import { createApp } from 'vue'
import Antd from 'ant-design-vue'
import { createPinia } from 'pinia'

import './style/index.less'
import snowy from './snowy'
import i18n from './locales'
import router from './router'
import App from './App.vue'
import './tailwind.css'



// import Vue from 'vue';
// import ElementUI from 'element-ui';
// import 'element-ui/lib/theme-chalk/index.css';
// import Vue from "@highlightjs/vue-plugin/src/vue";
//
// Vue.use(ElementUI);
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'



const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(Antd)
app.use(ElementPlus)
app.use(i18n)
app.use(snowy)

// 挂载app
app.mount('#app')
