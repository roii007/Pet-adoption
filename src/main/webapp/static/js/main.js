// 导入组件
const Home = { template: '#home-template' };
const Login = { template: '#login-template' };
const Register = { template: '#register-template' };
const PetList = { template: '#pet-list-template' };
const PetDetail = { template: '#pet-detail-template' };
const Apply = { template: '#apply-template' };
const MyApplications = { template: '#my-applications-template' };
const Profile = { template: '#profile-template' };
const AdminIndex = { template: '#admin-index-template' };
const AdminUsers = { template: '#admin-users-template' };
const AdminPets = { template: '#admin-pets-template' };
const AdminPetForm = { template: '#admin-pet-form-template' };
const AdminApplications = { template: '#admin-applications-template' };
const AdminApplicationDetail = { template: '#admin-application-detail-template' };

// 路由守卫
const checkAuth = (to, from, next) => {
    const user = $user.get();
    if (to.path.startsWith('/admin')) {
        if (!user) {
            next('/login');
        } else if (user.role !== 'admin') {
            next('/');
        } else {
            next();
        }
    } else if (to.path === '/apply' || to.path === '/profile' || to.path === '/my-applications') {
        if (!user) {
            next('/login');
        } else {
            next();
        }
    } else {
        next();
    }
};

const router = new VueRouter({
    routes: [
        { path: '/', component: Home },
        { path: '/login', component: Login },
        { path: '/register', component: Register },
        { path: '/pets', component: PetList },
        { path: '/pet/:id', component: PetDetail },
        { path: '/apply/:petId', component: Apply },
        { path: '/my-applications', component: MyApplications },
        { path: '/profile', component: Profile },
        { path: '/admin', component: AdminIndex, beforeEnter: checkAuth },
        { path: '/admin/users', component: AdminUsers, beforeEnter: checkAuth },
        { path: '/admin/pets', component: AdminPets, beforeEnter: checkAuth },
        { path: '/admin/pet-form/:id?', component: AdminPetForm, beforeEnter: checkAuth },
        { path: '/admin/applications', component: AdminApplications, beforeEnter: checkAuth },
        { path: '/admin/application/:id', component: AdminApplicationDetail, beforeEnter: checkAuth }
    ]
});

const app = new Vue({
    router,
    el: '#app'
});