using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Student2020ClientNoSSL_Kit
{
    public enum Direction
    {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NOACTION
    }

    public class Playfield
    {
        public int fieldWidth { get; set; }
        public int fieldHeight { get; set; }
        public List<Cell> mytrain { get; set; }
        public List<List<Cell>> othertrains { get; set; }
        public List<Cell> cargoList { get; set; }
        public List<Cell> obsticles { get; set; }
        public List<EnergyStation> energyStations { get; set; }
        public Direction currentDirection { get; set; }
        public int energyLevel { get; set; }
    }

    public struct Cell
    {
        public int X { get; set; }
        public int Y { get; set; }
    }

    public class EnergyStation
    {
        public Cell point { get; set; }
        public bool IsActive { get; set; }
        public int timeToActiveCountdown { get; set; }
    }
}
