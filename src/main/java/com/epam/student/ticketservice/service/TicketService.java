package com.epam.student.ticketservice.service;

import com.epam.student.ticketservice.model.Ticket;

import java.util.List;

public interface TicketService {

    /**
     * Получение списка билетов по ID рейса
     * @param id
     * @return
     */
    List<Ticket> getTicketsByPlaneId (Long id);

    /**
     * Вывод билетов по запросу: проданные, не проданные, все билеты
     * @param id
     * @param isSold
     * @return
     */

    List <Ticket> getTicketsByPlaneIdWithQuery (Long id,Boolean isSold);

    /**
     * Порлучение информации по одному билету
      * @param planeid
     * @param ticketid
     * @return
     */
    Ticket getTicketByIdWithPlaneId (Long planeid, Long ticketid);

    /**
     * Редактор билета
     * @param ticket
     */
    void editTicket (Ticket ticket);

    /**
     * Помечает билет на удаление
     * @param ticketid
     */
    void markTicketToDelete (Long ticketid);


}
