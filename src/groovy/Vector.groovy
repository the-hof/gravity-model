package gravity

class Vector {
    Double magnitude
    Double x
    Double y
    Double z

    Vector getUnitVector() {
        return new Vector(
                magnitude: 1,
                x: this.x/this.magnitude,
                y: this.y/this.magnitude,
                z: this.z/this.magnitude
        )
    }

    void setMagnitude() {
        this.magnitude = Math.sqrt(this.x**2 + this.y**2 + this.z**2)
    }

    Vector getScaledVector(Double scale_factor) {
        def result = new Vector(
                x: this.x * scale_factor,
                y: this.y * scale_factor,
                z: this.z * scale_factor
        )

        result.setMagnitude()

        return result
    }
}
