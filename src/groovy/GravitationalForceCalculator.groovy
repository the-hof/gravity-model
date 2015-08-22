package gravity

public final class GravitationalForceCalculator {
    private GravitationalForceCalculator() {}

    public static Force GetGravitationalForce(double G, Body thisObject, Body thatObject, Timestep timestep) {
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

        Double force_x = thatObject.x - thisObject.x
        Double force_y = thatObject.y - thisObject.y
        Double force_z = thatObject.z - thisObject.z
        Double force_size = Math.abs(force_x) + Math.abs(force_y) + Math.abs(force_z)

        return new Force(
                timestep: timestep,
                thisBody: thisObject,
                causingBody: thatObject,
                magnitude: magnitude,
                fx: magnitude * (force_x/force_size),
                fy: magnitude * (force_y/force_size),
                fz: magnitude * (force_z/force_size)
        )

    }

}
