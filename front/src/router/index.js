import Vue from 'vue';
import VueRouter from 'vue-router';

import Login from "@/views/Login";
import Register from "@/views/Register";
import Home from "@/views/Home";
import Reservations from "@/views/Reservations";
import Company from "@/views/Company";
import TicketEdit from "@/views/TicketEdit";

Vue.use(VueRouter);

const routes = [
{
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/reg',
    name: 'register',
    component: Register
  },
    {
        path: '/home',
        name: 'home',
        component: Home
    },
    {
        path: '/res',
        name: 'res',
        component: Reservations
    },
    {
        path: '/company/:name',
        name: 'company',
        component: Company
    },
    {
        path: '/editticket/:id',
        name: 'editTicket',
        component: TicketEdit
    }

];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
