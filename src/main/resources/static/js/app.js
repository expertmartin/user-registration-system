var app = angular.module('userregistrationsystem', [ 'ngRoute', 'ngResource' ]);
app.config(function($routeProvider) {
	$routeProvider.when('/list-all-users', {
		templateUrl : '/templates/listuser.html',
		controller : 'listUserController'
	}).when('/register-new-user', {
		templateUrl : '/templates/userregistration.html',
		controller : 'registerUserController'
	}).when('/update-user/:id', {
		templateUrl : '/templates/userupdation.html',
		controller : 'usersDetailsController'
	}).otherwise({
		redirectTo : '/home',
		templateUrl : '/templates/home.html',
	});
});