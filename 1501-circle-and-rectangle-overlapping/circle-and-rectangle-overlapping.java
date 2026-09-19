class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {

        // Find the closest x-coordinate on the rectangle
        int closestX = Math.max(x1, Math.min(xCenter, x2));

        // Find the closest y-coordinate on the rectangle
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Calculate the distance between the circle center
        // and the closest point of the rectangle
        int dx = xCenter - closestX;
        int dy = yCenter - closestY;

        // Check if the distance is within the circle's radius
        return dx * dx + dy * dy <= radius * radius;
    }
}