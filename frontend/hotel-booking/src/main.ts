import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http';

// Configuración de provideHttpClient en main.ts
bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    ...(appConfig.providers || []),
    provideHttpClient(), // Asegúrate de incluir provideHttpClient aquí
  ],
}).catch((err) => console.error(err));
