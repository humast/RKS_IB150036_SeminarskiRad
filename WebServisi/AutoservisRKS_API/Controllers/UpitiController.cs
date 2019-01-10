using AutoservisRKS_API.Helper;
using AutoservisRKS_API.Models;
using AutoservisRKS_API.ModelVM;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;

namespace AutoservisRKS_API.Controllers
{
    public class UpitiController : AuthToken
    {
        private AutoservisRKSEntities db = new AutoservisRKSEntities();

        // GET: api/Upiti
        [HttpGet]
        [Route("api/upiti/getUpitiByKlijentID/{id}")]
        public IHttpActionResult getUpitiByKlijentID(string id)
        {
            if (ProvjeriValidnostTokena() == false)
                return Unauthorized();

            int IDint = Convert.ToInt32(id);


            UpitiResultVM model = new UpitiResultVM
            {
                rows = db.Upiti.Where(k => k.KlijentID == IDint).Select(s => new UpitiResultVM.Row
                {

                    UpitID = s.UpitID,
                    DatumSlanja = s.DatumSlanja.ToString(),
                    MarkaAuta = s.MarkaAuta,
                    KlijentID = s.KlijentID


                }).ToList()
            };

            return Ok(model);

        }


        // POST: api/Upiti
        [ResponseType(typeof(Upiti))]
        public IHttpActionResult PostUpiti([FromBody]UpitiPostVM upit)
        {
            if (ProvjeriValidnostTokena() == false)
                return Unauthorized();

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            Upiti u = new Upiti();
            u.MarkaAuta = upit.MarkaAuta;
            u.PodaciOMotoru = upit.PodaciOMotoru;
            u.OpisKvara = upit.OpisKvara;
            u.KlijentID = upit.KlijentID;
            u.DatumSlanja = upit.DatumSlanja;

            db.Upiti.Add(u);
            db.SaveChanges();


            return Ok();
        }
    }
}
