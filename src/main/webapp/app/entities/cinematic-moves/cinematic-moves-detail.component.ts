import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICinematicMoves } from 'app/shared/model/cinematic-moves.model';

@Component({
  selector: 'jhi-cinematic-moves-detail',
  templateUrl: './cinematic-moves-detail.component.html'
})
export class CinematicMovesDetailComponent implements OnInit {
  cinematicMoves: ICinematicMoves | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cinematicMoves }) => {
      this.cinematicMoves = cinematicMoves;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
