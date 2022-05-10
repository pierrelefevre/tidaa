using System;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Caching.Memory;

namespace Student2020ClientNoSSL.Controllers
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
            return Ok(new { teamName = "Blue" });
        }     
    }
}
