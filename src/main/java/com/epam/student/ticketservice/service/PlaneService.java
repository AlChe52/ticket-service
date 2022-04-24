package com.epam.student.ticketservice.service;


import com.epam.student.ticketservice.model.Plane;

import java.util.List;

public interface PlaneService {

    /**
     * Получение всех рейсво с текущей даты
     */
    List<Plane> getAllPlanesFromCurrentDate();

    /**
     * Поиск рейса по Id
     * @param id
     * @return
     */
    Plane getPlaneById (Long id);

    /**
     * Добавляет рейс, при создание рейса автоматический добавляется количество билетов в зависимости от мест
     * @param plane
     */
    void addPlane (Plane plane);

    /**
     * Обновление информации о рейсе
     * @param plane
     */
    void editPlane (Plane plane);

    /**
     * Помечает рейс на удаление, IsDeleted = true, также помечаются все билеты
     * @param id
     */
   void markDeletePlane (Long id);




}
