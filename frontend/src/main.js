import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import PrimeVue from 'primevue/config';
import router from './router'

import Button from 'primevue/button';
import Menubar from 'primevue/menubar';
import DataView from 'primevue/dataview';
import Dialog from 'primevue/dialog';
import Toolbar from 'primevue/toolbar'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import InputNumber from 'primevue/inputnumber';
import DataViewLayoutOptions from 'primevue/dataviewlayoutoptions';
import Dropdown from 'primevue/dropdown';
import Textarea from 'primevue/textarea';
import Skeleton from 'primevue/skeleton';

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
app.component('Dialog', Dialog);
app.component('Toolbar', Toolbar);
app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('InputText', InputText);
app.component('InputNumber', InputNumber);
app.component('DataViewLayoutOptions', DataViewLayoutOptions);
app.component('Dropdown', Dropdown);
app.component('Textarea', Textarea);
app.component('Skeleton', Skeleton);


app.mount('#app');
