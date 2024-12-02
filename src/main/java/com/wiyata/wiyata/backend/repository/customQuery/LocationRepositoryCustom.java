package com.wiyata.wiyata.backend.repository.customQuery;


import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.entity.location.type.*;
import com.wiyata.wiyata.backend.payload.response.RestaurantResponse;
import com.wiyata.wiyata.backend.payload.response.location.*;

import java.util.List;

public interface LocationRepositoryCustom {

    List<Location> findLocationsByMemberId(Long memberId);

    AttractionResponse findAttractionByLocationId(Long locationId);

    CultureResponse findCultureByLocationId(Long locationId);

    FestivalResponse findFestivalByLocationId(Long locationId);

    LeportsResponse findLeportByLocationId(Long locationId);

    LodgeResponse findLodgeByLocationId(Long locationId);

    RestaurantResponse findRestaurantByLocationId(Long locationId);

    List<AttractionResponse> findAttractionsByLocationIds(List<Long> locationIds);

    List<CultureResponse> findCulturesByLocationIds(List<Long> locationIds);

    List<FestivalResponse> findFestivalsByLocationIds(List<Long> locationIds);

    List<LeportsResponse> findLeportsByLocationIds(List<Long> locationIds);

    List<LodgeResponse> findLodgesByLocationIds(List<Long> locationIds);

    List<RestaurantResponse> findRestaurantsByLocationIds(List<Long> locationIds);

    Long saveAttraction(Attraction attraction);

    Long saveCulture(Culture culture);

    Long saveLeports(Leports leports);

    Long saveLodge(Lodge lodge);

    Long saveFestival(Festival festival);

    Long saveRestaurant(Restaurant restaurant);
}
