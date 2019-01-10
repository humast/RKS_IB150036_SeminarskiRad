using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace AutoservisRKS_API.ModelVM
{
    public class UpitiResultVM
    {
        public class Row
        {
            public int UpitID { get; set; }
            public string MarkaAuta { get; set; }
            public int KlijentID { get; set; }
            public string DatumSlanja { get; set; }

        }
        public List<Row> rows;
    }
}