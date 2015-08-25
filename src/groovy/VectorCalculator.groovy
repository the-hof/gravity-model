package gravity

public final class VectorCalculator {
    private VectorCalculator() { }

    public static Vector SumVectors(Vector[] vectors) {
        Vector resultVector = new Vector(x: 0, y: 0, z: 0)

        for (Vector vector: vectors) {
            resultVector.x = resultVector.x + vector.x
            resultVector.y = resultVector.y + vector.y
            resultVector.z = resultVector.z + vector.z
        }

        def magnitude = Math.sqrt(resultVector.x**2 + resultVector.y**2 + resultVector.z**2)

        resultVector.setMagnitude()

        return resultVector
    }

    public static Vector SubtractVectors(Vector source, Vector target) {
        def matrix = new Vector[2]

        matrix[0] = target
        matrix[1] = source.getScaledVector(-1)

        return SumVectors(matrix)
    }
}
