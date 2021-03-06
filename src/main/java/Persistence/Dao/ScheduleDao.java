package Persistence.Dao;

import Persistence.Entity.Schedule;
import Persistence.Entity.Station;
import Persistence.Entity.Train;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.sql.Time;
import java.util.List;

/**
 * @author vbuevich
 *
 * DAO class for Schedule entity
 */
public class ScheduleDao {

    private static final Logger LOGGER = Logger.getLogger(ScheduleDao.class);

    /**
     * Method, used during ticketing
     *
     * @param stationName Departure station name
     * @param departureTime Time of departure
     * @return List<Schedule> that meets the requirements - time of departure greater than given time
     */
    public static List<Schedule> scheduleForDeparture(String stationName, String departureTime) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Schedule> trainList = null;

        try {
            Query q = session.createQuery("FROM Schedule s WHERE s.stationName = :st AND TIME(s.time) > TIME(:departureTime)");
            q.setParameter("st", stationName);
            q.setParameter("departureTime", departureTime);

            trainList = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return trainList;
    }

    /**
     * Method, used during ticketing
     *
     * @param stationName Arrival station name
     * @param arrivalTime Time of arrival
     * @return List<Schedule> that meets the requirements - time of departure is less than given time
     */
    public static List<Schedule> scheduleForArrival(String stationName, String arrivalTime) {
        Session session = DaoFactory.getSessionFactory().openSession();
        List<Schedule> trainList = null;

        try {
            Query q = session.createQuery("FROM Schedule s WHERE s.stationName = :st AND TIME(s.time) < TIME(:arrivalTime)");

            q.setParameter("st", stationName);
            q.setParameter("arrivalTime", arrivalTime);
            trainList = q.list();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            session.close();
        }
        return trainList;
    }

    /**
     * Method that adds new schedule
     *
     * @param tNumber Train number
     * @param station Station
     * @param departureTime The time of departure of train
     * @return true if schedule is successfully added , false if fail
     */
    public static Boolean addSchedule(int tNumber, String station, Time departureTime) {

        Session session = DaoFactory.getSessionFactory().openSession();
        Boolean isSuccess = true;

        try {
            Schedule s = new Schedule();
            s.setStationName(station);
            s.setStationByStationName((Station)session.get(Station.class, station));
            s.setTime(departureTime);
            s.setTrainNumber(tNumber);
            s.setTrainByTrainNumber((Train)session.get(Train.class, tNumber));

            session.save(s);
        }
        catch (Exception e) {
            isSuccess = false;
            LOGGER.error(e.getMessage());
        }
        finally {
            session.close();
            return isSuccess;
        }
    }

    /**
     * Method that receives the scheduled time of departure for given train
     *
     * @param trainNumber Number of train
     * @param departureS Departure station
     * @return time of departure
     */
    public static Time getTimeOfDeparture(int trainNumber, String departureS) {

        Session session = DaoFactory.getSessionFactory().openSession();

        Query q4 = session.createQuery("SELECT time FROM Schedule WHERE trainNumber = :tn AND stationName = :sn");
        q4.setParameter("tn", trainNumber);
        q4.setParameter("sn", departureS);
        Time timeOfDeparture = ((Time)q4.uniqueResult()); // uniqueness is granted by database table Unique restriction
        return timeOfDeparture;
    }
}
