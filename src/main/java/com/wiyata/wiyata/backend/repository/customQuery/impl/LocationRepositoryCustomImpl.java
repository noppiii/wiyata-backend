package com.wiyata.wiyata.backend.repository.customQuery.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.entity.location.QLocation;
import com.wiyata.wiyata.backend.entity.location.QMemberLocation;
import com.wiyata.wiyata.backend.entity.location.type.*;
import com.wiyata.wiyata.backend.payload.response.RestaurantResponse;
import com.wiyata.wiyata.backend.payload.response.location.*;
import com.wiyata.wiyata.backend.repository.customQuery.LocationRepositoryCustom;

import java.util.List;

public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public LocationRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Location> findLocationsByMemberId(Long memberId) {
        QLocation location = QLocation.location;
        QMemberLocation memberLocation = QMemberLocation.memberLocation;

        return jpaQueryFactory.selectFrom(location)
                .join(memberLocation).on(location.id.eq(memberLocation.locationId))
                .where(memberLocation.memberId.eq(memberId))
                .fetch();
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

    @Override
    public List<AttractionResponse> findAttractionsByLocationIds(List<Long> locationIds) {
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
                .where(attraction.locationId.in(locationIds))
                .fetch();
    }

    @Override
    public List<CultureResponse> findCulturesByLocationIds(List<Long> locationIds) {
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
                .where(culture.locationId.in(locationIds))
                .fetch();
    }

    @Override
    public List<FestivalResponse> findFestivalsByLocationIds(List<Long> locationIds) {
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
                .where(festival.locationId.in(locationIds))
                .fetch();
    }

    @Override
    public List<LeportsResponse> findLeportsByLocationIds(List<Long> locationIds) {
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
                .where(leports.locationId.in(locationIds))
                .fetch();
    }

    @Override
    public List<LodgeResponse> findLodgesByLocationIds(List<Long> locationIds) {
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
                .where(lodge.locationId.in(locationIds))
                .fetch();
    }

    @Override
    public List<RestaurantResponse> findRestaurantsByLocationIds(List<Long> locationIds) {
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
                .where(restaurant.locationId.in(locationIds))
                .fetch();
    }

    @Override
    public Long saveAttraction(Attraction attraction) {
        QAttraction qAttraction = QAttraction.attraction;

        long attractionId = jpaQueryFactory.insert(qAttraction)
                .columns(
                        qAttraction.locationId,
                        qAttraction.parking,
                        qAttraction.restDate,
                        qAttraction.useTime
                )
                .values(
                        attraction.getLocationId(),
                        attraction.isParking(),
                        attraction.getRestDate(),
                        attraction.getUseTime()
                )
                .execute();

        return attractionId;
    }

    @Override
    public Long saveCulture(Culture culture) {
        QCulture qCulture = QCulture.culture;

        long cultureId = jpaQueryFactory.insert(qCulture)
                .columns(
                        qCulture.locationId,
                        qCulture.parking,
                        qCulture.restDate,
                        qCulture.fee,
                        qCulture.useTime,
                        qCulture.spendTime
                )
                .values(
                        culture.getLocationId(),
                        culture.isParking(),
                        culture.getRestDate(),
                        culture.getFee(),
                        culture.getUseTime(),
                        culture.getSpendTime()
                )
                .execute();

        return cultureId;
    }

    @Override
    public Long saveLeports(Leports leports) {
        QLeports qLeports = QLeports.leports;

        long leportsId = jpaQueryFactory.insert(qLeports)
                .columns(
                        qLeports.locationId,
                        qLeports.openPeriod,
                        qLeports.parking,
                        qLeports.reservation,
                        qLeports.restDate,
                        qLeports.fee,
                        qLeports.useTime
                )
                .values(
                        leports.getLocationId(),
                        leports.getOpenPeriod(),
                        leports.isParking(),
                        leports.getReservation(),
                        leports.getRestDate(),
                        leports.getFee(),
                        leports.getUseTime()
                )
                .execute();

        return leportsId;
    }

    @Override
    public Long saveLodge(Lodge lodge) {
        QLodge qLodge = QLodge.lodge;

        long lodgeId = jpaQueryFactory.insert(qLodge)
                .columns(
                        qLodge.locationId,
                        qLodge.checkInTime,
                        qLodge.checkOutTime,
                        qLodge.cooking,
                        qLodge.parking,
                        qLodge.numOfRooms,
                        qLodge.reservationUrl,
                        qLodge.subFacility
                )
                .values(
                        lodge.getLocationId(),
                        lodge.getCheckInTime(),
                        lodge.getCheckOutTime(),
                        lodge.isCooking(),
                        lodge.isParking(),
                        lodge.getNumOfRooms(),
                        lodge.getReservationUrl(),
                        lodge.getSubFacility()
                )
                .execute();

        return lodgeId;
    }

    @Override
    public Long saveFestival(Festival festival) {
        QFestival qFestival = QFestival.festival;

        long festivalId = jpaQueryFactory.insert(qFestival)
                .columns(
                        qFestival.locationId,
                        qFestival.endDate,
                        qFestival.homepage,
                        qFestival.place,
                        qFestival.startDate,
                        qFestival.placeInfo,
                        qFestival.playTime,
                        qFestival.program,
                        qFestival.fee
                )
                .values(
                        festival.getLocationId(),
                        festival.getEndDate(),
                        festival.getHomepage(),
                        festival.getPlace(),
                        festival.getStartDate(),
                        festival.getPlaceInfo(),
                        festival.getPlayTime(),
                        festival.getProgram(),
                        festival.getFee()
                )
                .execute();

        return festivalId;
    }

    @Override
    public Long saveRestaurant(Restaurant restaurant) {
        QRestaurant qRestaurant = QRestaurant.restaurant;

        long restaurantId = jpaQueryFactory.insert(qRestaurant)
                .columns(
                        qRestaurant.locationId,
                        qRestaurant.popularMenu,
                        qRestaurant.openTime,
                        qRestaurant.packing,
                        qRestaurant.parking,
                        qRestaurant.restDate,
                        qRestaurant.menu
                )
                .values(
                        restaurant.getLocationId(),
                        restaurant.getPopularMenu(),
                        restaurant.getOpenTime(),
                        restaurant.isPacking(),
                        restaurant.isParking(),
                        restaurant.getRestDate(),
                        restaurant.getMenu()
                )
                .execute();

        return restaurantId;
    }

}
