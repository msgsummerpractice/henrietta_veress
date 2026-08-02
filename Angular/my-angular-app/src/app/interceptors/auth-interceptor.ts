import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer token',
    },
  });
  return next(cloned).pipe(
    catchError((error) => {
      console.log('HTTP error:', error.status, error.message);
      return throwError(() => error);
    }),
  );
};
