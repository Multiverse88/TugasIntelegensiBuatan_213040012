package tugasib;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

	class City {
	    private String name;
	    private double latitude;
	    private double longitude;
	
	    public City(String name, double latitude, double longitude) {
	        this.name = name;
	        this.latitude = latitude;
	        this.longitude = longitude;
	    }
	
	    public String getName() {
	        return name;
	    }
	
	    public double getLatitude() {
	        return latitude;
	    }
	
	    public double getLongitude() {
	        return longitude;
	    }
	}
	
	class DistanceCalculator {
	    public static double calculateDistance(City city1, City city2) {
	        double earthRadius = 6371; // Radius Bumi dalam kilometer
	        double lat1 = Math.toRadians(city1.getLatitude());
	        double lon1 = Math.toRadians(city1.getLongitude());
	        double lat2 = Math.toRadians(city2.getLatitude());
	        double lon2 = Math.toRadians(city2.getLongitude());
	
	        double dLat = lat2 - lat1;
	        double dLon = lon2 - lon1;
	
	        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	                 + Math.cos(lat1) * Math.cos(lat2)
	                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
	
	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	        return earthRadius * c; // Jarak dalam kilometer
	    }
	}
	
	public class PenghitungjarakAntarKota {
	    public static void main(String[] args) {
	        Map<String, City> cities = new HashMap<>();
	
	        // 1. Inisialisasi Data Kota
	        cities.put("Bandung", new City("Bandung", -6.9034, 107.6137));
	        cities.put("Bogor", new City("Bogor", -6.5946, 106.7894));
	        cities.put("Cirebon", new City("Cirebon", -6.7323, 108.5523));
	        cities.put("Sukabumi", new City("Sukabumi", -6.9250, 106.9271));
	        cities.put("Garut", new City("Garut", -7.2038, 107.9075));
	
	        Scanner scanner = new Scanner(System.in);
	
	        System.out.println("Pilih kota asal:");
	        int index = 1;
	        Map<Integer, String> cityIndex = new HashMap<>();
	        for (String cityName : cities.keySet()) {
	            System.out.println(index + ". " + cityName);
	            cityIndex.put(index, cityName);
	            index++;
	        }
	
	        int choice1 = scanner.nextInt();
	        if (cityIndex.containsKey(choice1)) {
	            // 2. Memilih Kota Asal
	            String city1Name = cityIndex.get(choice1);
	            City city1 = cities.get(city1Name);
	
	            System.out.println("Pilih kota tujuan:");
	            index = 1;
	            for (String cityName : cities.keySet()) {
	                System.out.println(index + ". " + cityName);
	                cityIndex.put(index, cityName);
	                index++;
	            }
	
	            int choice2 = scanner.nextInt();
	            if (cityIndex.containsKey(choice2)) {
	            	
	                // 3. Memilih Kota Tujuan
	                String city2Name = cityIndex.get(choice2);
	                City city2 = cities.get(city2Name);
	
	                // 4. Menghitung Jarak Terdekat
	                double distance = DistanceCalculator.calculateDistance(city1, city2);
	                System.out.printf("Jarak terdekat dari %s ke %s: %.2f km\n", city1Name, city2Name, distance);
	
	                // 5. Menentukan Rute Melalui Kota Mana
	                Stack<String> route = new Stack<>();
	                route.push(city2Name);
	                String currentCity = city2Name;
	
	                while (!currentCity.equals(city1Name)) {
	                    for (String cityName : cities.keySet()) {
	                        if (!cityName.equals(currentCity)) {
	                            City current = cities.get(currentCity);
	                            City next = cities.get(cityName);
	                            double currentDistance = DistanceCalculator.calculateDistance(current, next);
	                            double remainingDistance = DistanceCalculator.calculateDistance(next, cities.get(city1Name));
	
	                            if (currentDistance + remainingDistance == distance) {
	                                route.push(cityName);
	                                currentCity = cityName;
	                                break;
	                            }
	                        }
	                    }
	                }
	
	                // 6. Menampilkan Hasil
	                System.out.println("Rute melalui kota:");
	                while (!route.isEmpty()) {
	                    System.out.print(route.pop());
	                    if (!route.isEmpty()) {
	                        System.out.print(" -> ");
	                    }
	                }
	            } else {
	                System.out.println("Pilihan kota tujuan tidak valid.");
	            }
	        } else {
	            System.out.println("Pilihan kota asal tidak valid.");
	        }
	    }
	}
