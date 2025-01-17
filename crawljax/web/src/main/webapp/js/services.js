app.service('socket', ['$rootScope',  '$timeout', 'notificationService', 'historyHttp', function($rootScope, $timeout, notificationService, historyHttp){
	this.executionQueue = [];
	this.log = '';
	this.updateQueue = function(id, status){
		var element = this.executionQueue.find(function(item){
			return (item.id == id);
		});
		if (element != null)
			element.crawlStatus = status;
		$rootScope.$broadcast('queue-update', {newQueue: this.executionQueue});
	};
	this.removeQueue = function(id) {
		var element = this.executionQueue.find(function(item){
			return (item.id == id);
		});
		if (element != null)
			this.executionQueue.splice(this.executionQueue.indexOf(element), 1);
		$rootScope.$broadcast('queue-update', {newQueue: this.executionQueue});
	};
	this.socket = null;
	this.connectSocket = function(){
		var loc = window.location, host;
		if (loc.protocol === "https:") {
			host = "wss:";
		} else {
			host = "ws:";
		}
		host += "//" + loc.host;
		host += loc.pathname + "socket";
		try {
			var service = this;
			this.socket = new WebSocket(host);
			this.socket.onmessage = function(msg){
				$timeout(function(){
					if (msg.data.indexOf('log-') == 0){
						service.log += '<p>' + (msg.data.slice(4)) + '</p>';
						$rootScope.$broadcast('log-update', {newLog: service.log});
					}
					if (msg.data.indexOf('queue-') == 0) {
						var record = JSON.parse(msg.data.slice(6));
						record.plugins = [];
						service.executionQueue.push(record);
					}
					if (msg.data.indexOf('init-') == 0)
						service.updateQueue(msg.data.slice(5), "initializing");
					if (msg.data.indexOf('run-') == 0)
						service.updateQueue(msg.data.slice(4), "running");
					if (msg.data.indexOf('fail-') == 0) {
						service.updateQueue(msg.data.slice(5), "failure");
						setTimeout(function(){service.removeQueue(msg.data.slice(5));}, 5000);
					}
					if (msg.data.indexOf('success-') == 0) {
						service.updateQueue(msg.data.slice(8), "success");
						setTimeout(function(){service.removeQueue(msg.data.slice(8));}, 5000);
					}
					if (msg.data.indexOf('message-') == 0) {
						var positivity = 0;
						if (msg.data.indexOf('success-') == 8) {
							positivity = 1;
							notificationService.notify(msg.data.slice(16), positivity);
						} else if (msg.data.indexOf('error-') == 8) {
							positivity = -1;
							notificationService.notify(msg.data.slice(14), positivity);
						} else {
							notificationService.notify(msg.data.slice(8), positivity);
						}
					}
					console.log(msg);
				});
			}
			this.socket.onclose = function(){
				service.connectSocket();
			};
		}catch(exception){
			 alert('Error'+exception);
		}
	};
	
	this.sendMsg = function(text){
		try{
			var self = this;
			if (self.socket.readyState != 1) {
				setTimeout(function(){ self.socket.send(text); }, 500);
			}
			else self.socket.send(text);
		} catch(exception){
			alert("Socket Timed out. Refresh your browser.");
		}
   }
}]);

app.service('configHttp', ['$http', 'notificationService', function($http, notificationService){
	this.getConfigurations = function(){
		var request = $http({
		    url: '/rest/configurations',
		    method: 'GET'
		});
		return request.then(function(result){
			return result.data;
		}, function(error){
			return [];
		});
	};
	this.getConfiguration = function(configId){
		var request = $http({
			url: '/rest/configurations/' + configId,
		    method: 'GET'
		});
		return request.then(function(result){
			return result.data;
		}, function(error){
			return {};
		});
	};
	this.getNewConfiguration = function(){
		var request = $http({
		    url: '/rest/configurations/new',
		    method: 'GET',
		});
		return request.then(function(result){
			return result.data;
		}, function(error){
			return {};
		})
	};
	this.postConfiguration = function(config){
		var request = $http({
			method: 'POST',
			url: '/rest/configurations',
			data: angular.toJson(config)
		});
		return request.then(function(result){
			notificationService.notify("Configuration Saved", 1);
			return result;
		}, function(error){
			notificationService.notify("Error Saving Configuration", -1);
		})
	};
	this.updateConfiguration = function(config, configId){
		var request = $http({
			url: '/rest/configurations/' + configId,
		    method: 'PUT',
		    data: angular.toJson(config)
		});
		return request.then(function(result){
			notificationService.notify("Configuration Saved", 1);
		}, function(error){
			notificationService.notify("Error Saving Configuration", -1);
		})
	};
	this.deleteConfiguration = function(config, configId){
		var request = $http({
			method: 'DELETE',
			url: '/rest/configurations/' + configId,
			headers: {
				"Accept": "application/json, text/javascript, */*; q=0.01",
				'Content-Type': "application/json; charset=UTF-8;"
			},
			data: angular.toJson(config)
		});
		return request.then(function(result){
			notificationService.notify("Configuration Deleted", 1);
		}, function(error){
			notificationService.notify("Error Deleting Configuration", -1);
		})
	};
}]);

app.service('configAdd', [function(){
	this.addClickRule = function(config){
		config.clickRules.push({rule: 'click', elementTag: 'a', conditions: []});
	}
	this.addClickRuleCondition = function(config, index){
		config.clickRules[index].conditions.push({condition: 'wAttribute', expression: ''});
	}
	this.deleteClickRuleCondition = function(config, ruleIndex, conditionIndex){
		config.clickRules[ruleIndex].conditions.splice(conditionIndex, 1);
	}
	this.deleteClickRule = function(config, index){
		config.clickRules.splice(index, 1);
	}
	this.addCondition = function(config){
		config.pageConditions.push({condition: 'url', expression: ''});
	};
	this.deleteCondition = function(config, index){
		config.pageConditions.splice(index, 1);
	};
	this.addFilter = function(config){
		config.comparators.push({type: 'attribute', expression: ''});
	};
	this.deleteFilter = function(config, index){
		config.comparators.splice(index, 1);
	};
	this.addFormField = function(config){
		config.formInputValues.push({name: '', value: ''});
	};
	this.deleteFormField = function(config, index){
		config.formInputValues.splice(index, 1);
	};
	this.addInvariant = function(config){
		config.invariants.push({condition: 'url', expression: ''});
	};
	this.deleteInvariant = function(config, index){
		config.invariants.splice(index, 1);
	};
	this.addPlugin = function(config){
		config.plugins.push({});
	};
	this.deletePlugin = function(config, index){
		config.plugins.splice(index, 1);
	};
}]);

app.service('testHttp', ['$http', function($http) {

	/**
	 * Retrieves the test history from the server.
	 */
	this.getTestHistory = function(active) {
		var data = ""
		if(active) data = {active: true};
		var request = $http({
			method: 'GET',
			url: '/rest/tests',
			params: data
		});
		return request.then(function(result){
			return result.data;
		});
	}

	/**
	 * Retrieves a results from a single test from the server.
	 */
	this.getTest = function(testId) {
		var request = $http({
			method: 'GET',
			url: '/rest/tests/' + testId
		});
		return request.then(function(result){
			return result.data;
		});
	}

	/**
	 * Adds a new JUnit test run to the queue.
	 */
	this.addTestRecord = function() {
		var request = $http({
			method: 'POST',
			url: '/rest/tests'
		});
		return request.then(function(result) {
			return result.data;
		});
	}

	/**
	* Gets image in the test output folder
	*/
	this.getTestImage = function(imageName, testId){
		var request = $http({
			method : 'GET',
			url: 'rest/tests/image',
			params : {'imageName' : imageName, 'testId' : testId}
		});
		return request.then(function(result){
			return result.data;
		});
	}

	/**
	* Gets image data for a give diff in a test run 
	*/
	this.getTestImageData = function(stateName, testId){
		var request = $http({
			method : 'GET',
			url: 'rest/tests/imageData',
			params : {'stateName' : stateName, 'testId' : testId}
		});
		return request.then(function(result){
			return result.data;
		});
	}

	this.getTestImages = function(testId){
		var request = $http({
			method : 'GET',
			url: 'rest/tests/allImagesOfTest',
			params : {'testId' : testId}
		});
		return request.then(function(result){
			return result.data;
		});
	}

}]);

app.service('historyHttp', ['$http', 'notificationService', function($http, notificationService){
	this.getHistory = function(active){
		var data = ""
		if(active) data = {active: true};
		var request = $http({
			method: 'GET',
			url: '/rest/history',
			params: data
		});
		return request.then(function(result){
			result.data.forEach(function(record){
				var pluginArray = [];
				pluginArray.push({key: "0", name: "Crawl Overview"});
				for(key in record.plugins){
					record.plugins[key].key = key;
					pluginArray.push(record.plugins[key]);
				}
				record.plugins = pluginArray;
			})
			return result.data;
		});
	};
	this.getCrawl = function(crawlId){
		var request = $http({
			method: 'GET',
			url: '/rest/history/' + crawlId
		});
		return request.then(function(result){
			var pluginArray = [];
			pluginArray.push({key: "0", name: "Crawl Overview"});
			for(key in result.data.plugins){
				result.data.plugins[key].key = key;
				pluginArray.push(result.data.plugins[key]);
			}
			result.data.plugins = pluginArray;
			return result.data;
		});
	};
	this.addCrawl = function(config){
		var request = $http({
			method: 'POST',
			url: '/rest/history',
			data: config.id
		});
		
		return request.then(function(result){
			var pluginArray = [];
			pluginArray.push({key: "0", name: "Crawl Overview"});
			for(key in result.data.plugins){
				result.data.plugins[key].key = key;
				pluginArray.push(result.data.plugins[key]);
			}
			result.data.plugins = pluginArray;
		});
	};
}]);

app.service('restService', ['$rootScope', function($rootScope){
	this.rest = null;
	this.changeRest = function(newRest){
		this.rest = newRest;
		$rootScope.$broadcast('restChanged');
	};
}]);

app.service('notificationService', function(){
	this.notify = function(text, positivity) {
		var clazz = "info";
		if(positivity > 0) clazz = "success";
		if(positivity < 0) clazz = "error";
		$('#notification').removeClass().addClass("alert").addClass("alert-" + clazz).text(text);
		clearTimeout(this.messageTimeout);
		this.messageTimeout = setTimeout(function() {
			$('#notification').removeClass().addClass("alert").addClass("alert-mute");
		}, 3000);
  };
})
