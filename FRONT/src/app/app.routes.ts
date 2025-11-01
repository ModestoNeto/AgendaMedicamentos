import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Login } from './pages/login/login';
import { Cadastro } from './pages/cadastro/cadastro';
import { Agenda } from './pages/agenda/agenda';
import { Medicamentos } from './pages/medicamentos/medicamentos';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: Home
  },
  {
    path: 'agenda',
    component: Agenda
  },
  {
    path: 'medicamentos',
    component: Medicamentos
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'cadastro',
    component: Cadastro
  }
];
