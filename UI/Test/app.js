var app = angular.module("app", ['ngRoute']);
app.config(['$routeProvider',
    function($routeProvider,$location) {
        $routeProvider.
            when('/', {

                templateUrl:'index1.html',
                controller: 'RouteController'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);

app.directive('mineMap', function() {
        // directive link function
        var link = function(scope, element, attrs) {
            var map, infoWindow;
            var markers = [];

            // map config
            var mapOptions = {
                center: new google.maps.LatLng(37.339728,-121.870201),
                zoom: 13,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                scrollwheel: false
            };

            // init the map
            function initMap() {
                if (map === void 0) {
                    map = new google.maps.Map(element[0], mapOptions);
                }
            }

            // place a marker
            function setMarker(map, position, title, content) {
                var marker;
                if(content=="main")
                {
                    //alert("hhhh")
                    var markerOptions = {
                        position: position,
                        map: map,
                        title: title,
                        icon: 'https://maps.google.com/mapfiles/ms/icons/red-dot.png'
                    };
                }
                else if(content=="amb") {
                    var markerOptions = {
                        position: position,
                        map: map,
                        title: title,
                        icon: 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png'
                    };
                }
                else {
                    var markerOptions = {
                        position: position,
                        map: map,
                        title: title,
                        icon: 'https://maps.google.com/mapfiles/ms/icons/green-dot.png'
                    };
                }

                marker = new google.maps.Marker(markerOptions);
                markers.push(marker); // add marker to array
                var trafficLayer = new google.maps.TrafficLayer();
                trafficLayer.setMap(map);

                google.maps.event.addListener(marker, 'click', function () {
                    // close window if not undefined
                    if (infoWindow !== void 0) {
                        infoWindow.close();
                    }
                    // create new window
                    var infoWindowOptions = {
                        content: content
                    };
                    infoWindow = new google.maps.InfoWindow(infoWindowOptions);
                    infoWindow.open(map, marker);
                });
            }
            function drawCircle(populationOptions)
            {
                cityCircle = new google.maps.Circle(populationOptions);
            }
            scope.$watch(function() {
                var lat1=37.334498;
                var lng1=-121.876469;
                scope.myLocations=[];
                var config = {
                    method: 'GET',
                    url: 'http://10.0.0.9:8080/getData'
                };
                $http(config)
                    .success(function(data) {
                        scope.myLocations=data;

                    });
                    return scope.myLocations;

            }, function() {
                initMap();
                // clear markers
                for (var i = 0; i < markers.length; i++ ) {
                    markers[i].setMap(null);
                }
                markers = [];

                angular.forEach(scope.myLocations, function(value, key){
                    // a single object in this example could be:
                    // { lat: 50, lon: 3, title: "my title", content: "my content" }
                    var location = new google.maps.LatLng(value.lat, value.lon);
                    setMarker(map, location, value.title, value.content);

                });
                setMarker(map, new google.maps.LatLng(37.349247,-121.862819),"", "main");
                setMarker(map, new google.maps.LatLng(37.328400,-121.877475),"", "main");

                var populationOptions = {
                    strokeColor: '#FF0000',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#FF0000',
                    fillOpacity: 0.1,
                    map: map,
                    center: new google.maps.LatLng(37.339728,-121.870201),
                    radius: 1500
                };
                // Add the circle for this city to the map.
                drawCircle(populationOptions);


            });
        };

        return {
            restrict: 'A',
            template: '<div id="gmaps"></div>',
            replace: true,
            link: link
        };
    });


app.controller("RouteController", function($scope,$http,$timeout,$location)
{
    $timeout(
        function(){
            $location.path('/')
        }, 60000);

});

