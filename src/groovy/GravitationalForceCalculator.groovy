package gravity

public final class GravitationalForceCalculator {
    private GravitationalForceCalculator() {}

    public static Vector CalculateGravitationalForce(double G, Body thisObject, Body thatObject, Timestep timestep, String calcType) {
        Double thisMass = thisObject.mass
        Double thatMass = thatObject.mass

        Double distance_squared = (thisObject.x - thatObject.x)**2
        distance_squared += (thisObject.y - thatObject.y)**2
        distance_squared += (thisObject.z - thatObject.z)**2

        Double distance = Math.sqrt(distance_squared)

        Double magnitude = (G * thisMass * thatMass) / distance_squared

        Double scale_ratio = (distance/magnitude)

        if (magnitude == 0) {
            return new Vector(
                    magnitude: 0, x: 0, y: 0, z: 0)
        }

        Double x = thatObject.x - thisObject.x
        Double y = thatObject.y - thisObject.y
        Double z = thatObject.z - thisObject.z

        return new Vector(
                magnitude: magnitude,
                x: (x/scale_ratio),
                y: (y/scale_ratio),
                z: (z/scale_ratio)
        )

    }

}
