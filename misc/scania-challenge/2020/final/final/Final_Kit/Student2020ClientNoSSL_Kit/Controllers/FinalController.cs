using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Caching.Memory;

namespace Student2020ClientNoSSL_Kit.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FinalController : ControllerBase
    {
        private readonly IMemoryCache _cache;
        Random random = new Random();

        public FinalController(IMemoryCache memoryCache)
        {
            _cache = memoryCache;
        }

        [HttpGet]
        public IActionResult Get()
        {
            return Ok(new { teamName = "Your teamname" });
        }


        [HttpPost]
        public IActionResult Post([FromBody] Playfield playfield)
        {
            //Place your code here

            //This is an example that returns a direction. You can use this to get started.
            Direction d1 = ExampleRandomWithBoundsCheck(playfield);
            return Ok(d1);
        }


        //EXAMPLE CODE TO USE STATE
        private Direction ExampleUsingState(Playfield playfield)
        {
            //Get my directions stored in state
            var x = GetStoredDirections();
            //If I don't have any directions stored, add 10 direction
            if (x.Count == 0)
            {
                for (int i = 0; i < 10; i++)
                {
                    DirectionsPush(playfield.currentDirection);
                }
            }
            //Return first in list (FIFO list)
            return DirectionsPop();
        }


        //EXAMPLE CODE WITH RANDOM MOVEMENT AND BOUNDS CHECK
        private Direction ExampleRandomWithBoundsCheck(Playfield playfield)
        {
            Random random = new Random();
            Direction newDir = playfield.currentDirection;
            bool badDirection = true;
            while (badDirection)
            {
                //80% chance to turn
                if (random.Next(100) > 80)
                    newDir = (Direction)random.Next(4);

                //You can't turn 180 deg. Get new direction
                while (IsOppositeDirection(newDir, playfield.currentDirection))
                {
                    newDir = (Direction)random.Next(4);
                }

                //Check playfield bounds. Assume direction is good. Check for bad conditions
                badDirection = false;
                switch (newDir)
                {
                    case Direction.UP:
                        if (playfield.mytrain[0].Y == 0)
                            badDirection = true;
                        break;
                    case Direction.DOWN:
                        if (playfield.mytrain[0].Y == playfield.fieldHeight - 1)
                            badDirection = true;
                        break;
                    case Direction.LEFT:
                        if (playfield.mytrain[0].X == 0)
                            badDirection = true;
                        break;
                    case Direction.RIGHT:
                        if (playfield.mytrain[0].X == playfield.fieldWidth - 1)
                            badDirection = true;
                        break;
                }

            }

            return newDir;
        }


        //Utility method: Get your stored list of directions as a list
        private List<Direction> GetStoredDirections()
        {
            if (!_cache.TryGetValue("StoredDirections", out List<Direction> dirs))
            {
                //dirs = JsonConvert.DeserializeObject<List<Direction>>(await System.IO.File.ReadAllTextAsync("countrylist.json"));
                dirs = new List<Direction>();
                MemoryCacheEntryOptions options = new MemoryCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromSeconds(25), // cache will expire in 25 seconds
                    SlidingExpiration = TimeSpan.FromSeconds(5) // caceh will expire if inactive for 5 seconds
                };
                _cache.Set("StoredDirections", dirs, options);
            }
            return dirs;
        }

        //Utility method: If you build up a list of directions, store then with this method
        private void SetStoredDirections(List<Direction> dirs)
        {
            _cache.Remove("StoredDirections");
            MemoryCacheEntryOptions options = new MemoryCacheEntryOptions
            {
                AbsoluteExpirationRelativeToNow = TimeSpan.FromSeconds(60), // cache will expire in 25 seconds
                SlidingExpiration = TimeSpan.FromSeconds(60) // caceh will expire if inactive for 5 seconds
            };
            _cache.Set("StoredDirections", dirs, options);
        }

        //Utility method: If you want to use push and pop methods to store your list, use this to PUSH a new Direction to your list
        private void DirectionsPush(Direction dir)
        {
            var d = GetStoredDirections();
            d.Add(dir);
            SetStoredDirections(d);
        }

        //Utility method: If you want to use push and pop methods to store your list, use this to POP a new Direction to your list
        private Direction DirectionsPop()
        {
            var d = GetStoredDirections();
            if (d.Count > 0)
            {
                Direction direction = d.First();
                d.RemoveAt(0);
                SetStoredDirections(d);
                return direction;
            }
            return Direction.NOACTION;
        }

        //Utility method. Check if new direction is the opposite of your current
        private bool IsOppositeDirection(Direction dir, Direction dir2)
        {
            if (dir == Direction.RIGHT && dir2 == Direction.LEFT) return true;
            if (dir == Direction.LEFT && dir2 == Direction.RIGHT) return true;

            if (dir == Direction.UP && dir2 == Direction.DOWN) return true;
            if (dir == Direction.DOWN && dir2 == Direction.UP) return true;

            return false;
        }
    }
}
