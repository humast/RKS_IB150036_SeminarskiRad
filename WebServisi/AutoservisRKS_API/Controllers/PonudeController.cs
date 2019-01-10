using AutoservisRKS_API.Helper;
using AutoservisRKS_API.Models;
using AutoservisRKS_API.ModelVM;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;

namespace AutoservisRKS_API.Controllers
{
    public class PonudeController : AuthToken
    {
        private AutoservisRKSEntities db = new AutoservisRKSEntities();

        [HttpGet]
        [Route("api/Ponude/GetPonudaByID/{id}")]
        public IHttpActionResult GetPonudaByID(string id)
        {
            if (ProvjeriValidnostTokena() == false)
                return Unauthorized();

            int upitID = Convert.ToInt32(id);

            Ponude ponuda = db.Ponude.OrderByDescending(x=>x.PonudaID).Where(x => x.UpitID == upitID).FirstOrDefault();

            PonudeResultVM p = new PonudeResultVM();

            p.PonudaID = ponuda.PonudaID;
            p.MarkaAuta = ponuda.MarkaAuta;
            p.Cijena = ponuda.Cijena.ToString();
            p.DatumPocetka = ponuda.DatumPocetka.ToString("dd/MM/yyyy hh:mm");
            p.DatumZavrsetka = ponuda.DatumZavrsetka.ToString("dd/MM/yyyy hh:mm");
            p.Odgovoren = Convert.ToBoolean(ponuda.Odgovoren);
            p.Prihvacen = Convert.ToBoolean(ponuda.Prihvacen);
            p.UpitID = upitID;

            return Ok(p);
        }


        [HttpGet]
        [Route("api/Ponude/GetPostojanjePonude/{id}")]
        public IHttpActionResult GetPostojanjePonude(string id)
        {
            if (ProvjeriValidnostTokena() == false)
                return Unauthorized();

            int upitID = Convert.ToInt32(id);

            Ponude ponuda = db.Ponude.FirstOrDefault(x => x.UpitID == upitID);
            if (ponuda == null)
                return Ok("Nije kreirana");
            else
                return Ok("Postoji");
        }


        // PUT: api/Upiti/5
        [HttpPut]
        [ResponseType(typeof(void))]
        public IHttpActionResult PutPonude(PonudeResultVM p)
        {
            if (ProvjeriValidnostTokena() == false)
                return Unauthorized();

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            Ponude izmijenjena = new Ponude()
            {
                PonudaID = p.PonudaID,
                Cijena = Convert.ToDecimal(p.Cijena),
                DatumPocetka=Convert.ToDateTime(p.DatumPocetka),
                DatumZavrsetka=Convert.ToDateTime(p.DatumZavrsetka),
                MarkaAuta=p.MarkaAuta,
                Odgovoren=p.Odgovoren,
                Prihvacen=p.Prihvacen,
                UpitID=p.UpitID
            };
            db.Entry(izmijenjena).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }

            //return StatusCode(HttpStatusCode.NoContent);
            return Ok();
        }

    }
}
