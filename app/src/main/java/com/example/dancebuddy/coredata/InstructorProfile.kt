package com.example.dancebuddy.coredata

import androidx.compose.ui.graphics.painter.Painter

data class InstructorProfile (
    override val id: Int,
    override val name: String,
    override val surname: String,
    override val email: String,
    override val password: String,
    override val image: Painter?
) : UserProfile