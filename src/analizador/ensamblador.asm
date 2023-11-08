.MODEL SMALL
.CODE

INICIO:
    mov Ax, @Data
    mov Ds, Ax 

;Impresion de constante MSJ1
    mov Dx, offset MSJ1
    mov ah, 09h
    int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;Leer de variables a1
   mov ah, 0Ah
   mov Dx, Offset a1
   int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;ciclo condicional mientras
    Xor Ax,Ax
repet1:
    mov Si, offSet a1+2
    mov Ah, byte ptr [Si]
    mov Di, offset 1+2
    mov Al, 1
    Add Al, 30h
    cmp Al,Ah
JNE     rep1:
;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;Impresion de constante MSJ2
    mov Dx, offset MSJ2
    mov ah, 09h
    int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;Leer de variables b1
   mov ah, 0Ah
   mov Dx, Offset b1
   int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;ciclo for
    mov Si, offset a1+2
    mov byte ptr[Si], 1+30h
    xor Cx, Cx
    mov Si, offset b1+2
    mov Cl, byte ptr[Si]
    sub Cl, 30h
    sub Cl, 1-1
ciclo1:
    push Cx

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;Impresion de constante MSJ3
    mov Dx, offset MSJ3
    mov ah, 09h
    int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;Leer de variables c1
   mov ah, 0Ah
   mov Dx, Offset c1
   int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;condicional si
    xor Ax, Ax
    mov Si, offSet c1+2
    mov Ah, byte ptr [Si]
    mov Di, offset 5+2
    mov Al, 5
    Add Al, 30h
    CMP Ah,Al
JA wi0:
;Impresion de constante MSJ4
    mov Dx, offset MSJ4
    mov ah, 09h
    int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

wi0:
;condicional si
    xor Ax, Ax
    mov Si, offSet c1+2
    mov Ah, byte ptr [Si]
    mov Di, offset 5+2
    mov Al, 5
    Add Al, 30h
    CMP Ah,Al
JBE wi1:
;Impresion de constante MSJ5
    mov Dx, offset MSJ5
    mov ah, 09h
    int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

wi1:
;condicional si
    xor Ax, Ax
    mov Si, offSet c1+2
    mov Ah, byte ptr [Si]
    mov Di, offset 8+2
    mov Al, 8
    Add Al, 30h
    CMP Ah,Al
JBE wi2:
;Impresion de constante MSJ6
    mov Dx, offset MSJ6
    mov ah, 09h
    int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

wi2:
    mov Si, offset a1+2
    Add byte ptr[Si],1
    POP CX
Loop ciclo1
;Impresion de constante MSJ7
    mov Dx, offset MSJ7
    mov ah, 09h
    int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

;Leer de variables a1
   mov ah, 0Ah
   mov Dx, Offset a1
   int 21h

;salto de linea
    mov Dx, offset Salto
    mov ah,09h
    int 21h

JMP repet1:
    rep1:
    mov Ax, 4C00H
    INT 21H

.Data

    Salto DB 10,13, '$'
  a1 DB 5,?,5 dup (24h)
  c1 DB 5,?,5 dup (24h)
  b1 DB 5,?,5 dup (24h)
  MSJ1 db  'Deseas ingresar calificaciones? 1=SI 0=NO' ,10,13,'$'
  MSJ2 db  'Ingresa el total de alumnos' ,10,13,'$'
  MSJ3 db  'Ingresa tu calificacion' ,10,13,'$'
  MSJ4 db  'Tu calificacion es reprobatoria' ,10,13,'$'
  MSJ5 db  'Tu calificacion es aprobatoria' ,10,13,'$'
  MSJ6 db  'Tu calificacion es exelente' ,10,13,'$'
  MSJ7 db  'Deseas ingresar mas calificaciones? 1=SI 0=NO' ,10,13,'$'

.Stack

END INICIO
