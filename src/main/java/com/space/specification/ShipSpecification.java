package com.space.specification;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ShipSpecification {

    public static Specification<Ship> getSpecification(String name,
                                                 String planet,
                                                 ShipType shipType,
                                                 Long after, Long before,
                                                 Boolean isUsed,
                                                 Double minSpeed, Double maxSpeed,
                                                 Integer minCrewSize, Integer maxCrewSize,
                                                 Double minRating, Double maxRating) {
        return Specification.where(ShipSpecification.shipsName(name)
                .and(ShipSpecification.shipsPlanet(planet)))
                .and(ShipSpecification.shipsShipType(shipType))
                .and(ShipSpecification.shipsDate(after, before))
                .and(ShipSpecification.shipsUsed(isUsed))
                .and(ShipSpecification.shipsSpeed(minSpeed, maxSpeed))
                .and(ShipSpecification.shipsCrewSize(minCrewSize, maxCrewSize))
                .and(ShipSpecification.shipsRating(minRating, maxRating));
    }

    public static Specification<Ship> shipsRating(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null)                return cb.lessThanOrEqualTo(root.get("rating"), max);
            if (max == null)                return cb.greaterThanOrEqualTo(root.get("rating"), min);
            return cb.between(root.get("rating"), min, max);
        };
    }

    public static Specification<Ship> shipsName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }


    public static Specification<Ship> shipsPlanet(String planet) {
        return (root, query, cb) -> planet == null ? null : cb.like(root.get("planet"), "%" + planet + "%");
    }


    public static Specification<Ship> shipsShipType(ShipType shipType) {
        return (root, query, cb) -> shipType == null ? null : cb.equal(root.get("shipType"), shipType);
    }


    public static Specification<Ship> shipsDate(Long after, Long before) {
        return (root, query, cb) -> {
            if (after == null && before == null) return null;
            if (after == null) {
                Date before1 = new Date(before);
                return cb.lessThanOrEqualTo(root.get("prodDate"), before1);
            }
            if (before == null) {
                Date after1 = new Date(after);
                return cb.greaterThanOrEqualTo(root.get("prodDate"), after1);
            }
            Date before1 = new Date(before);
            Date after1 = new Date(after);
            return cb.between(root.get("prodDate"), after1, before1);
        };
    }


    public static Specification<Ship> shipsUsed(Boolean isUsed) {
        return (root, query, cb) -> {
            if (isUsed == null) return null;
            if (isUsed)         return cb.isTrue(root.get("isUsed"));
            else return cb.isFalse(root.get("isUsed"));
        };
    }


    public static Specification<Ship> shipsSpeed(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null)                return cb.lessThanOrEqualTo(root.get("speed"), max);
            if (max == null)                return cb.greaterThanOrEqualTo(root.get("speed"), min);
            return cb.between(root.get("speed"), min, max);
        };
    }


    public static Specification<Ship> shipsCrewSize(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null)                return cb.lessThanOrEqualTo(root.get("crewSize"), max);
            if (max == null)                return cb.greaterThanOrEqualTo(root.get("crewSize"), min);
            return cb.between(root.get("crewSize"), min, max);
        };
    }
}
