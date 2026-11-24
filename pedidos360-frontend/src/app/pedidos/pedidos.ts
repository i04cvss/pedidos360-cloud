
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './pedidos.html',
  styleUrl: './pedidos.css'
})
export class PedidosComponent implements OnInit {

  pedidos: any[] = [];

  estadoSeleccionado: string = 'TODOS';
  estadoFiltro: string = 'TODOS';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/api/pedidos')
      .subscribe({
        next: (response) => {
          this.pedidos = response;
          console.log('Pedidos cargados:', this.pedidos);
        },
        error: (error) => {
          console.error('Error al cargar los pedidos:', error);
        }
      });
  }

  aplicarFiltro(): void {
    this.estadoFiltro = this.estadoSeleccionado.trim().toUpperCase();

    console.log('Filtro aplicado:', this.estadoFiltro);
    console.log('Resultado:', this.pedidosFiltrados);
  }

  get pedidosFiltrados(): any[] {
    if (this.estadoFiltro === 'TODOS') {
      return this.pedidos;
    }

    return this.pedidos.filter(pedido =>
      pedido.estado?.trim().toUpperCase() === this.estadoFiltro
    );
  }
}
