import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import PrimeVue from 'primevue/config';
import router from './router'

import Button from 'primevue/button';
import Menubar from 'primevue/menubar';
import DataView from 'primevue/dataview';
import Rating from 'primevue/rating';

import "primevue/resources/themes/saga-blue/theme.css"       //theme
import "primevue/resources/primevue.min.css"                 //core css
import "primeicons/primeicons.css"                           //icons
import "./assets/styles.css";

const app = createApp(App)
app.use(PrimeVue);
app.use(router);

app.component('Button', Button);
app.component('Menubar', Menubar);
app.component('DataView', DataView);
app.component('Rating', Rating);

app.mount('#app');
