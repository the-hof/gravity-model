package gravity

public final class GravitationalForceCalculator {
    private GravitationalForceCalculator() {}

    public static Force GetGravitationalForce(double G, Body thisObject, Body thatObject) {
        Double thisMass = thisObject.mass
        Double thatMass = thatObject.mass

        Double distance_squared = (thisObject.x - thatObject.x)**2 +
                (thisObject.y - thatObject.y)**2 +
                (thisObject.z - thatObject.z)**2

        Double magnitude = (G * thisMass * thatMass) / distance_squared

        if (magnitude == 0) {
            return new Force(
                    thisBody: thisObject, causingBody: thatObject, magnitude: 0, fx: 0, fy: 0, fz: 0)
        }

        Double force_x = thisObject.x - thatObject.x
        Double force_y = thisObject.y - thatObject.y
        Double force_z = thisObject.z - thatObject.z
        Double force_size = force_x + force_y + force_z

        return new Force(
                timestep: thisObject.timestep,
                thisBody: thisObject,
                causingBody: thatObject,
                magnitude: magnitude,
                fx: magnitude * (force_x/force_size),
                fy: magnitude * (force_y/force_size),
                fz: magnitude * (force_z/force_size)
        )

    }

}
