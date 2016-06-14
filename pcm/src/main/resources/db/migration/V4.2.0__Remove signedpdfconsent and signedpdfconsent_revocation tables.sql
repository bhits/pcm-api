ALTER TABLE consent drop foreign key FK38B6C01A5FD7634E;
ALTER TABLE consent drop foreign key FK38B6C01AD5FBA41D;

ALTER TABLE consent drop column unsigned_pdf_consent;
ALTER TABLE consent drop column unsigned_pdf_consent_revoke;

DROP TABLE signedpdfconsent;
DROP TABLE signedpdfconsent_aud;

DROP TABLE signedpdfconsent_revocation;
DROP TABLE signedpdfconsent_revocation_aud;
