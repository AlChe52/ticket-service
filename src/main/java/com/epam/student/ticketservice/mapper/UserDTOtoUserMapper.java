package com.epam.student.ticketservice.mapper;

import com.epam.student.ticketservice.dto.UserDTO;
import com.epam.student.ticketservice.model.User;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class UserDTOtoUserMapper extends ConfigurableMapper {
    private MapperFactory factory = new DefaultMapperFactory.Builder().build();

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UserDTO.class, User.class)
                .field("firstname","firstname")
                .field("lastname","lastname")
                .field("passport","passport")
                .byDefault()
                .register();
    }
}
