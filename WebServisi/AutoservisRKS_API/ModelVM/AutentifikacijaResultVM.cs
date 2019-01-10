using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace AutoservisRKS_API.ModelVM
{
    public class AutentifikacijaResultVM
    {
        public int KlijentID { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string KorisnickoIme { get; set; }
        public string LozinkaSalt { get; set; }
        public string Telefon { get; set; }
        public string Email { get; set; }
        public string Token { get; set; }
    }
}