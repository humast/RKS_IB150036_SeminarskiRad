//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace AutoservisRKS_API.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class Ponude
    {
        public int PonudaID { get; set; }
        public string MarkaAuta { get; set; }
        public decimal Cijena { get; set; }
        public System.DateTime DatumPocetka { get; set; }
        public System.DateTime DatumZavrsetka { get; set; }
        public Nullable<bool> Prihvacen { get; set; }
        public Nullable<bool> Odgovoren { get; set; }
        public int UpitID { get; set; }
    
        public virtual Upiti Upiti { get; set; }
    }
}
