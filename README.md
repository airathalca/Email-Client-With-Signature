# Tugas-Besar-3-IF4020-Kriptografi
> Program Secure Client Email untuk mengenkripsi email serta membubuhi digital signature

## Daftar Isi

- [Deskripsi Singkat](#deskripsi-singkat)
- [Fitur Program](#fitur-program)
- [Cara Menjalankan Program](#cara-menjalankan-program)

## Deskrispi Singkat

Sebagian besar aplikasi e-mail client tidak memiliki fitur enkripsi surel dan fitur tanda tangan digital. Surel rawan untuk disadap dan dibaca oleh pihak yang tidak berhak, dimanipulasi, dan sebagainya. Enkripsi dapat digunakan untuk untuk menjaga kerahasiaan surel, sedangkan tanda tangan digital dapat digunakan untuk keperluan otentikasi (pengirim dan penerima surel), keaslian isi surel (data integrity), dan nirpenyangkalan (non-repudiation).

## Fitur Program

1. Klien surel dapat membangkitkan kunci publik dan kunci privat berdasarkan Elliptic Curve Cryptography.
2. Klien surel memiliki editor untuk mengetikkan isi surel, memasukkan alamat surel penerima, mengetikkan subyek surel, dll.
3. Klien surel dapat menampilkan inbox, sent email, dan fitur-fitur umum yang terdapat di dalam klien surel.
4. Klien surel boleh menyediakan attachment, tetapi file yang di-attach tidak dienkripsi.
5. Pengguna dapat memilih apakah surel ditandatangani atau tidak (ada toggle icon untuk memilihnya).
6. Jika pengguna memilih mengenkripsi/dekripsi surel, maka klien surel meminta pengguna memasukkan kunci.
7. Isi surel dienkripsi/dekripsi dengan block cipher yang sudah dibuat sebelumnya.
8. Program Keccak (SHA-3) dibuat sendiri oleh pemrogram (tidak menggunakan library atau fungsi built-in).
9. Jika pengguna memilih menandatangani surel (baik surel terenkripsi atau tidak), maka klien meminta kunci privat. Untuk memverifikasi tanda tangan digital, klien surel meminta kunci public. Kunci public/privat dapat dibaca dari file yang terdapat di dalam Android KeyStore.

## Cara Menjalankan Program

1. Pastikan Anda telah memiliki program Android Studio pada mesin Anda.
2. Clone repository ini pada Android Studio.
3. Lakukan perintah Build Gradle untuk membangun aplikasi.
4. Jalankan aplikasi tersebut pada emulator atau device Android yang Anda miliki.
