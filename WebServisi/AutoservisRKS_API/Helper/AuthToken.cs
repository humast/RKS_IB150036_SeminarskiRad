using AutoservisRKS_API.Models;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace AutoservisRKS_API.Helper
{
    public class AuthToken: ApiController
    {
        private AutoservisRKSEntities db = new AutoservisRKSEntities();

        protected static string GetAuthToken()
        {
            string authToken = null;
            NameValueCollection headers = HttpContext.Current.Request.Headers;

            if (headers["AuthToken"] != null)
                authToken = headers["AuthToken"];

            return authToken;
        }

        protected bool ProvjeriValidnostTokena()
        {
            AutorizacijskiToken TokenCheck = db.GetTokenPoVrijednosti(GetAuthToken()).FirstOrDefault();

            if (TokenCheck != null)
            {
                if (TokenCheck.VrijemeEvidentiranja >= DateTime.Now.AddDays(-2)) // token moze biti star 2 dana
                {
                    return true;
                }
            }

            return false;
        }

        protected void IzbrisiToken()
        {
            AutorizacijskiToken TokenCheck = db.GetTokenPoVrijednosti(GetAuthToken()).FirstOrDefault();

            if (TokenCheck != null)
            {
                db.AutorizacijskiToken.Remove(TokenCheck);
                db.SaveChanges();
            }
        }
    }
}