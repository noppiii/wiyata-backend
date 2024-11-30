package com.wiyata.wiyata.backend.repository.customQuery.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiyata.wiyata.backend.entity.location.type.*;
import com.wiyata.wiyata.backend.payload.response.RestaurantResponse;
import com.wiyata.wiyata.backend.payload.response.location.*;
import com.wiyata.wiyata.backend.repository.customQuery.LocationRepositoryCustom;

public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public LocationRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public AttractionResponse findAttractionByLocationId(Long locationId) {
        QAttraction attraction = QAttraction.attraction;

        return jpaQueryFactory.select(
                        Projections.constructor(AttractionResponse.class,
                                attraction.id,
                                attraction.locationId,
                                attraction.parking,
                                attraction.restDate,
                                attraction.useTime)
                )
                .from(attraction)
                .where(attraction.locationId.eq(locationId))
                .fetchOne();
    }

    @Override
    public CultureResponse findCultureByLocationId(Long locationId) {
        QCulture culture = QCulture.culture;

        return jpaQueryFactory.select(
                        Projections.constructor(CultureResponse.class,
                                culture.id,
                                culture.locationId,
                                culture.parking,
                                culture.restDate,
                                culture.fee,
                                culture.useTime,
                                culture.spendTime)
                )
                .from(culture)
                .where(culture.locationId.eq(locationId))
                .fetchOne();
    }

    @Override
    public FestivalResponse findFestivalByLocationId(Long locationId) {
        QFestival festival = QFestival.festival;

        return jpaQueryFactory.select(
                        Projections.constructor(FestivalResponse.class,
                                festival.id,
                                festival.locationId,
                                festival.endDate,
                                festival.homepage,
                                festival.place,
                                festival.startDate,
                                festival.placeInfo,
                                festival.playTime,
                                festival.program,
                                festival.fee)
                )
                .from(festival)
                .where(festival.locationId.eq(locationId))
                .fetchOne();
    }

    @Override
    public LeportsResponse findLeportByLocationId(Long locationId) {
        QLeports leports = QLeports.leports;

        return jpaQueryFactory.select(
                        Projections.constructor(LeportsResponse.class,
                                leports.id,
                                leports.locationId,
                                leports.openPeriod,
                                leports.parking,
                                leports.reservation,
                                leports.restDate,
                                leports.fee,
                                leports.useTime)
                )
                .from(leports)
                .where(leports.locationId.eq(locationId))
                .fetchOne();
    }

    @Override
    public LodgeResponse findLodgeByLocationId(Long locationId) {
        QLodge lodge = QLodge.lodge;

        return jpaQueryFactory.select(
                        Projections.constructor(LodgeResponse.class,
                                lodge.id,
                                lodge.locationId,
                                lodge.checkInTime,
                                lodge.checkOutTime,
                                lodge.cooking,
                                lodge.parking,
                                lodge.numOfRooms,
                                lodge.reservationUrl,
                                lodge.subFacility)
                )
                .from(lodge)
                .where(lodge.locationId.eq(locationId))
                .fetchOne();
    }

    @Override
    public RestaurantResponse findRestaurantByLocationId(Long locationId) {
        QRestaurant restaurant = QRestaurant.restaurant;

        return jpaQueryFactory.select(
                        Projections.constructor(RestaurantResponse.class,
                                restaurant.id,
                                restaurant.locationId,
                                restaurant.popularMenu,
                                restaurant.openTime,
                                restaurant.packing,
                                restaurant.parking,
                                restaurant.restDate,
                                restaurant.menu)
                )
                .from(restaurant)
                .where(restaurant.locationId.eq(locationId))
                .fetchOne();
    }
}
