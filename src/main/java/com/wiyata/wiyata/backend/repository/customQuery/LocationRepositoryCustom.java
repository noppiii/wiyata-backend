package com.wiyata.wiyata.backend.repository.customQuery;


import com.wiyata.wiyata.backend.payload.response.RestaurantResponse;
import com.wiyata.wiyata.backend.payload.response.location.*;

public interface LocationRepositoryCustom {

    AttractionResponse findAttractionByLocationId(Long locationId);

    CultureResponse findCultureByLocationId(Long locationId);

    FestivalResponse findFestivalByLocationId(Long locationId);

    LeportsResponse findLeportByLocationId(Long locationId);

    LodgeResponse findLodgeByLocationId(Long locationId);

    RestaurantResponse findRestaurantByLocationId(Long locationId);
}
