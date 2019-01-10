using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace AutoservisRKS_API.ModelVM
{
    public class PonudeResultVM
    {
        public int PonudaID;
        public string MarkaAuta;
        public string Cijena;
        public string DatumPocetka;
        public string DatumZavrsetka;
        public bool Prihvacen;
        public bool Odgovoren;
        public int UpitID;
    }
}