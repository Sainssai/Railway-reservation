import java.sql.*;
import java.util.*;

public class Train {
    public static class User {
        private String username;
        private String password;
        Scanner s = new Scanner(System.in);

        public String getUsername() {
            System.out.println("Enter your username: ");
            username = s.nextLine();
            return username;
        }

        public String getPassword() {
            System.out.println("Enter your password: ");
            password = s.nextLine();
            return password;
        }
    }

    public static class PnrStatus {
        private int pnrNumber;
        private String passengerName;
        private String trainNo;
        private String classType;
        private String dateOfJourney;
        private String startingPoint;
        private String destinationPoint;

        Scanner s = new Scanner(System.in);
        public static final int MIN = 10000;
        public static final int MAX = 99999;

        public int getPnrNumber() {
            Random rn = new Random();
            pnrNumber = rn.nextInt((MAX - MIN) + 1) + MIN;
            return pnrNumber;
        }

        public String getPassengerName() {
            System.out.println("Enter the passenger name: ");
            passengerName = s.nextLine();
            return passengerName;
        }

        public String getTrainNo() {
            System.out.println("Enter the train no: ");
            trainNo = s.nextLine();
            return trainNo;
        }

        public String getClassType() {
            System.out.println("Enter the class type: ");
            classType = s.nextLine();
            return classType;
        }

        public String getDateOfJourney() {
            System.out.println("Enter the journey date in YYYY-MM-DD format: ");
            dateOfJourney = s.nextLine();
            return dateOfJourney;
        }

        public String getStartingPoint() {
            System.out.println("Enter the starting point (source): ");
            startingPoint = s.nextLine();
            return startingPoint;
        }

        public String getDestinationPoint() {
            System.out.println("Enter your destination: ");
            destinationPoint = s.nextLine();
            return destinationPoint;
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        User u = new User();
        boolean b = true;
        String username = u.getUsername();
        String password = u.getPassword();
        String url = "jdbc:postgresql://localhost:5432/railway";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                System.out.println("User accessed successfully");
                while (b) {
                    System.out.println("Enter your choice: ");
                    System.out.println("1. Insert\n2. Delete\n3. Show all passenger details\n4. Show passenger detail\n5. Exit");
                    int choice = s.nextInt();
                    s.nextLine();  // Consume newline left-over

                    switch (choice) {
                        case 1:
                            PnrStatus p = new PnrStatus();
                            int pnrNumber = p.getPnrNumber();
                            String passengerName = p.getPassengerName();
                            String trainNo = p.getTrainNo();
                            String classType = p.getClassType();
                            String journeyDate = p.getDateOfJourney();
                            String from = p.getStartingPoint();
                            String to = p.getDestinationPoint();
                            try (PreparedStatement ps = con.prepareStatement("INSERT INTO reservation(pnrnumber, passengername, trainno, classtype, dateofjourney, startingpoint, destinationpoint) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                                ps.setInt(1, pnrNumber);
                                ps.setString(2, passengerName);
                                ps.setString(3, trainNo);
                                ps.setString(4, classType);
                                ps.setString(5, journeyDate);
                                ps.setString(6, from);
                                ps.setString(7, to);
                                int affectedRows = ps.executeUpdate();
                                if (affectedRows > 0) {
                                    System.out.println("Passenger details added successfully");
                                } else {
                                    System.out.println("Error while inserting passenger details");
                                }
                            } catch (Exception e) {
                                System.out.println("SQL Exception: " + e.getMessage());
                            }
                            break;
                        case 2:
                            System.out.println("Enter PNR number to delete passenger details: ");
                            int pnrNum = s.nextInt();
                            try (PreparedStatement p1 = con.prepareStatement("DELETE FROM reservation WHERE pnrnumber = ?")) {
                                p1.setInt(1, pnrNum);
                                int affectedRows = p1.executeUpdate();
                                if (affectedRows > 0) {
                                    System.out.println("Passenger details deleted successfully");
                                } else {
                                    System.out.println("Error in deleting passenger details");
                                }
                            } catch (SQLException e) {
                                System.out.println("SQL Exception: " + e.getMessage());
                            }
                            break;
                        case 3:
                            try (PreparedStatement p2 = con.prepareStatement("SELECT * FROM reservation")) {
                                ResultSet rs = p2.executeQuery();
                                while (rs.next()) {
                                    int pnrNumRetrieved = rs.getInt("pnrnumber");
                                    String pName = rs.getString("passengername");
                                    String trainNumber = rs.getString("trainno");
                                    String classTypeRetrieved = rs.getString("classtype");
                                    String dateJourney = rs.getString("dateofjourney");
                                    String fromLocation = rs.getString("startingpoint");
                                    String toLocation = rs.getString("destinationpoint");
                                    System.out.println("PNR NUMBER: " + pnrNumRetrieved);
                                    System.out.println("Passenger details: " + pName);
                                    System.out.println("Train no: " + trainNumber);
                                    System.out.println("Class type: " + classTypeRetrieved);
                                    System.out.println("Date of journey: " + dateJourney);
                                    System.out.println("Source: " + fromLocation);
                                    System.out.println("Destination: " + toLocation);
                                }
                            } catch (SQLException e) {
                                System.out.println("SQL Exception: " + e.getMessage());
                            }
                            break;
                        case 4:
                            System.out.println("Enter PNR number of person to get details: ");
                            int pnrNumDetail = s.nextInt();
                            try (PreparedStatement p3 = con.prepareStatement("SELECT * FROM reservation WHERE pnrnumber = ?")) {
                                p3.setInt(1, pnrNumDetail);
                                ResultSet rs = p3.executeQuery();
                                if (rs.next()) {
                                    int pnrNumRetrieved = rs.getInt("pnrnumber");
                                    String pName = rs.getString("passengername");
                                    String trainNumber = rs.getString("trainno");
                                    String classTypeRetrieved = rs.getString("classtype");
                                    String dateJourney = rs.getString("dateofjourney");
                                    String fromLocation = rs.getString("startingpoint");
                                    String toLocation = rs.getString("destinationpoint");
                                    System.out.println("PNR NUMBER: " + pnrNumRetrieved);
                                    System.out.println("Passenger details: " + pName);
                                    System.out.println("Train no: " + trainNumber);
                                    System.out.println("Class type: " + classTypeRetrieved);
                                    System.out.println("Date of journey: " + dateJourney);
                                    System.out.println("Source: " + fromLocation);
                                    System.out.println("Destination: " + toLocation);
                                } else {
                                    System.out.println("No passenger found with this PNR number");
                                }
                            } catch (Exception e) {
                                System.out.println("Exception: " + e.getMessage());
                            }
                            break;
                        case 5:
                            System.out.println("Exiting...");
                            b = false;
                            break;
                        default:
                            System.out.println("Enter a valid choice");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading drivers: " + e.getStackTrace());
        }
        s.close();
    }
}
