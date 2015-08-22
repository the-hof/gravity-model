package gravity

public final class GravitationalForceCalculator {
    private GravitationalForceCalculator() {}

    public static Force GetGravitationalForce(double G, Body thisObject, Body thatObject, Timestep timestep) {
        Double thisMass = thisObject.mass
        Double thatMass = thatObject.mass

        Double distance_squared = (thisObject.x - thatObject.x)**2 +
                (thisObject.y - thatObject.y)**2 +
                (thisObject.z - thatObject.z)**2

        Double distance = Math.sqrt(distance_squared)

        Double magnitude = (G * thisMass * thatMass) / distance_squared

        Double scale_ratio = (distance/magnitude)

        if (magnitude == 0) {
            return new Force(
                    thisBody: thisObject, causingBody: thatObject, magnitude: 0, fx: 0, fy: 0, fz: 0)
        }

        Double x = thatObject.x - thisObject.x
        Double y = thatObject.y - thisObject.y
        Double z = thatObject.z - thisObject.z


        return new Force(
                timestep: timestep,
                thisBody: thisObject,
                causingBody: thatObject,
                magnitude: magnitude,
                fx: (x/scale_ratio),
                fy: (y/scale_ratio),
                fz: (z/scale_ratio)
        )

    }

}
