package project.reservation;

import project.reservation.dao.ReservationDao;
import project.reservation.dto.ReservationDto;

import java.util.List;

public class ReservationService {


    public String reserveTicket(ReservationDto dto){
        return ReservationRepository.getInstance().reserveTicket(dto);
    }

    public int numberOfReservationsForUser(int userId) {
        return ReservationRepository.getInstance().countReservations(userId);
    }

    public List<ReservationDao> getUserReservations(int id){
        return ReservationRepository.getInstance().getUserReservation(id);
    }

    public String cancelReservation(int userId, int reservationId){
        return ReservationRepository.getInstance().deleteReservation(userId, reservationId);
    }

}
