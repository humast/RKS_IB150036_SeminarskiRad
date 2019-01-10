using AutoservisRKS_API.Helper;
using AutoservisRKS_API.Models;
using AutoservisRKS_API.ModelVM;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace AutoservisRKS_API.Controllers
{
    public class AutentifikacijaController : AuthToken
    {
        private AutoservisRKSEntities db = new AutoservisRKSEntities();

        [HttpGet]
        [Route("api/Autentifikacija/LoginCheck/{username}/{pass}")]
        public IHttpActionResult LoginCheck(string username, string pass)
        {

            string token = Guid.NewGuid().ToString();

            Klijenti k = db.Klijenti.Where(x => x.KorisnickoIme == username && x.LozinkaSalt == pass).FirstOrDefault(); // unutar lozinkaSalt je smjesten string password

            if (k != null)
            {
                AutentifikacijaResultVM a = new AutentifikacijaResultVM();
                a.KlijentID = k.KlijentID;
                a.Ime = k.Ime;
                a.Prezime = k.Prezime;
                a.KorisnickoIme = k.KorisnickoIme;
                a.LozinkaSalt = k.LozinkaSalt;
                a.Telefon = k.Telefon;
                a.Email = k.Email;
                a.Token = token;

                db.AutorizacijskiToken.Add(new AutorizacijskiToken
                {
                    Vrijednost = a.Token,
                    KlijentID = a.KlijentID,
                    VrijemeEvidentiranja = DateTime.Now,
                    IpAdresa = "..."
                });

                db.SaveChanges();

                return Ok(a);

            }

            AutentifikacijaResultVM y = new AutentifikacijaResultVM();
            y.Ime = "PogresniPodaci";

            return Ok(y);
        }


        [HttpDelete]
        [Route("api/Autentifikacija/Logout")]
        public IHttpActionResult Logout()
        {
            IzbrisiToken();

            return Ok();
        }
    }
}
