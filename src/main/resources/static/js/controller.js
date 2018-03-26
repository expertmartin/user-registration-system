app.controller('registerUserController', function($scope, $http, $location, $route) {
	$scope.submitUserForm = function() {
		$http({
			method : 'POST',
			url : 'http://localhost:8080/api/user/',
			data : $scope.user,
		}).then(function(response) {
			$scope.regresp1 = "REsponse returned.";
			$scope.regresp2 = response;
			$location.path("/list-all-users");
			$route.reload();
		}, function(errResponse) {
			$scope.errr1 = "error happened!"
			$scope.errrResp = errResponse;
			$scope.errrData = errResponse.data;
			$scope.errrmsg = errResponse.data.errorMessage;
			$scope.errorMessage = errResponse.data.errorMessage;
		});
	}
	$scope.resetForm = function() {
		$scope.user = null;
	};
});

app.controller('listUserController', function($scope, $http, $location, $route) {
	$http({
		method : 'GET',
		url : 'http://localhost:8080/api/user/'
	}).then(function(response) {
		$scope.resp = response;
		$scope.users = response.data;
	});
	$scope.editUser = function(userId) {
		$scope.uid = userId;
		$location.path("/update-user/" + userId);
	}
	$scope.deleteUser = function(userId) {
		$http({
			method : 'DELETE',
			url : 'http://localhost:8080/api/user/' + userId
		}).then(function(response) {
			$location.path("/list-allusers");
			$route.reload();
		});
	}
});

app.controller('usersDetailsController',function($scope, $http, $location, $routeParams, $route) {
	$scope.userId = $routeParams.id;

	$http({
	method : 'GET',
	url : 'http://localhost:8080/api/user/' + $scope.userId
	}).then(function(response) {
		$scope.updateresp1 = response;
		$scope.user = response.data;
	});
	$scope.submitUserForm = function() {
    	$http({
    	method : 'PUT',
    	url : 'http://localhost:8080/api/user/' + $scope.userId,
    	data : $scope.user,
	})
	.then(
			function(response) {
				$scope.updateresp2 = response;
    			$location.path("/list-all-users");
    			$route.reload();
			},
			function(errResponse) {
				$scope.errrResp = errResponse;
				$scope.errrData = errResponse.data;
				$scope.errrmsg = errResponse.data.errorMessage;
				$scope.errorMessage = "Error while updating User - Error Message: '"
				+ errResponse.data.errorMessage;
			});
			}
			});
