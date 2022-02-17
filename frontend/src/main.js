import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import PrimeVue from 'primevue/config';
import router from './router'

import Button from 'primevue/button';
import Menubar from 'primevue/menubar';
import DataView from 'primevue/dataview';
import Rating from 'primevue/rating';
import Dialog from 'primevue/dialog';
import Toolbar from 'primevue/toolbar'
import FileUpload from 'primevue/fileupload'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import Textarea from 'primevue/textarea';
import Dropdown from 'primevue/dropdown';
import RadioButton from 'primevue/radiobutton';
import InputNumber from 'primevue/inputnumber';


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
app.component('Dialog', Dialog);
app.component('Toolbar', Toolbar);
app.component('FileUpload', FileUpload);
app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('InputText', InputText);
app.component('TextArea', Textarea);
app.component('Dropdown', Dropdown);
app.component('RadioButton', RadioButton);
app.component('InputNumber', InputNumber);
app.component('Rating', Rating);


app.mount('#app');
