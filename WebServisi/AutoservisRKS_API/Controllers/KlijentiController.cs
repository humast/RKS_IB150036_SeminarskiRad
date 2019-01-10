using AutoservisRKS_API.Helper;
using AutoservisRKS_API.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;

namespace AutoservisRKS_API.Controllers
{
    public class KlijentiController : AuthToken
    {
        private AutoservisRKSEntities db = new AutoservisRKSEntities();


        public List<Klijenti> GetKlijenti()
        {
            return db.Klijenti.ToList();
        }


        // POST: api/Klijenti
        [ResponseType(typeof(Klijenti))]
        public IHttpActionResult PostKlijenti([FromBody] Klijenti k)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Klijenti.Add(k);
            

            try
            {
                db.SaveChanges();
            }
            catch (System.Data.Entity.Validation.DbEntityValidationException dbEx)
            {
                Exception raise = dbEx;
                foreach (var validationErrors in dbEx.EntityValidationErrors)
                {
                    foreach (var validationError in validationErrors.ValidationErrors)
                    {
                        string message = string.Format("{0}:{1}",
                            validationErrors.Entry.Entity.ToString(),
                            validationError.ErrorMessage);
                        // raise a new exception nesting
                        // the current instance as InnerException
                        raise = new InvalidOperationException(message, raise);
                    }
                }
                throw raise;
            }

            return Ok();
        }
    }
}
